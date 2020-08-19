package blockone.poc.client

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable

@CompileStatic
@Client(BintrayConfiguration.BINTRAY_API_URL)
interface BintrayClient {

    @Get('/api/${bintray.apiversion}/repos/${bintray.organization}/${bintray.repository}/packages')
    Flowable<BintrayPackage> fetchPackages()
}
