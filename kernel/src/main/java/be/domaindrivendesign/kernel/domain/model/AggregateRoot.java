package be.domaindrivendesign.kernel.domain.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.common.model.EntityStateType;

import java.util.UUID;

/*
An aggregate is a pattern that groups a set of associated objects (entity + value object) that are considered
as one unit with regards to data change. The aggregate is delimited by a boundary that
delimits the internal objects from the external objects.  Each aggregate as a root
object, the "aggregate root" that will be the only accessible from the outside (when
performing logic operation). The aggregate root as a reference to the entire objects
that comprise the aggregate, but an external object can only have a references to the
aggregate root. The aggregate root is the single point of acces to the aggregate.

The Aggregate root is associated to a repository to be persisted (if needed). It's a 1to1
relationship.
The other aggregate objects are persisted using the same repository (if needed).
The aggregate root never uses its associated repository, it's the domain service that
will orchestrate the link between the domain logic (entity) and the persistence logic (repository).

The domain service has to retrieve the aggregate objects trought the aggregate root
and never directly.

If an aggregate objects need to have access to the aggregate root, it will only contains
the Id of the aggregate root to avoid circular references. This aggregate root Id will
often be used as foreign key in the database (repository implementation).

Aggregate or aggregate root are statefull.

The method on the aggregate must represents real business method and by consequence validate the business method
and not the entire aggregate root (or aggregate). Method that are no business are not part of a DDD approach.

Based on the aggregate pattern.
*/

public class AggregateRoot extends Entity {
    protected AggregateRoot() {
        super();
    }

    protected AggregateRoot(UUID id, EntityStateType state) {
        super(id, state);
    }

    protected AggregateRoot(UUID id) {
        super(id);
    }
}
