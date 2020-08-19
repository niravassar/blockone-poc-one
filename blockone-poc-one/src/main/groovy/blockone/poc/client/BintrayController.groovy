package blockone.poc.client

import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Flowable
import io.reactivex.Maybe

@CompileStatic
@Controller("/bintray")
class BintrayController {
    private final BintrayLowLevelClient bintrayLowLevelClient

    private final BintrayClient bintrayClient

    BintrayController(BintrayLowLevelClient bintrayLowLevelClient,
                      BintrayClient bintrayClient) {
        this.bintrayLowLevelClient = bintrayLowLevelClient
        this.bintrayClient = bintrayClient
    }

    @Get("/packages-lowlevel")
    Maybe<List<BintrayPackage>> packagesWithLowLevelClient() {
        return bintrayLowLevelClient.fetchPackages()
    }

    @Get(uri = "/packages", produces = MediaType.APPLICATION_JSON_STREAM)
    Flowable<BintrayPackage> packages() {
        return bintrayClient.fetchPackages()
    }
}
