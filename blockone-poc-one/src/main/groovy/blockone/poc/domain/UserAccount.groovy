import grails.gorm.annotation.Entity
import io.micronaut.core.annotation.Introspected
import org.grails.datastore.gorm.GormEntity

@Introspected
@Entity
class UserAccount implements GormEntity<UserAccount> {
    String email
}
