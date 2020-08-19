package blockone.poc.client

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Flowable
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BintrayControllerSpec extends Specification {

    @Inject
    @Client("/")
    RxStreamingHttpClient client

    @Shared
    List<String> expectedProfileNames = ['base', 'federation', 'function', 'function-aws', 'service']

    def "Verify bintray packages can be fetched with low level HttpClient"() {
        when:
        HttpRequest request = HttpRequest.GET('/bintray/packages-lowlevel')

        HttpResponse<List<BintrayPackage>> rsp = client.toBlocking().exchange(request,
                Argument.listOf(BintrayPackage))

        then: 'the endpoint can be accessed'
        rsp.status == HttpStatus.OK
        rsp.body()

        when:
        List<BintrayPackage> packages = rsp.body()

        then:
        for (String name : expectedProfileNames) {
            assert packages*.name.contains(name)
        }
    }

    def "Verify bintray packages can be fetched with compile-time autogenerated @Client"() {
        when:
        HttpRequest request = HttpRequest.GET('/bintray/packages')

        Flowable<BintrayPackage> bintrayPackageStream = client.jsonStream(request, BintrayPackage)
        Iterable<BintrayPackage> bintrayPackages = bintrayPackageStream.blockingIterable()

        then:
        for (String name : expectedProfileNames) {
            assert bintrayPackages*.name.contains(name)
        }
    }
}