package com.meditrack.platform.u20241e275.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

/**
 * A physical naming strategy that converts entity names to snake case and pluralizes table names.
 * @author Joel Huamani Estefanero
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy implements PhysicalNamingStrategy {

    /**
     * Convert identifier to physical catalog name
     * @param identifier Identifier
     * @param jdbcEnvironment JdbcEnvironment
     * @return Identifier
     */
    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    /**
     * Convert identifier to physical schema name
     * @param identifier Identifier
     * @param jdbcEnvironment JdbcEnvironment
     * @return Identifier
     */
    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Convert identifier to physical table name
     * @param identifier Identifier
     * @param jdbcEnvironment JdbcEnvironment
     * @return Identifier
     */
    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(this.toPlural(identifier));
    }

    /**
     * Convert identifier to physical sequence name
     * @param identifier Identifier
     * @param jdbcEnvironment JdbcEnvironment
     * @return Identifier
     */
    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Convert identifier to physical column name
     * @param identifier Identifier
     * @param jdbcEnvironment JdbcEnvironment
     * @return Identifier
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    /**
     * Convert identifier to snake case
     * @param identifier Identifier
     * @return Identifier
     */
    private Identifier toSnakeCase(final Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = identifier.getText()
                .replaceAll(regex, replacement)
                .toLowerCase();
        return Identifier.toIdentifier(newName);
    }

    /**
     * Convert identifier to plural
     * @param identifier Identifier
     * @return Identifier
     */
    private Identifier toPlural(final Identifier identifier) {
        final String newName = pluralize(identifier.getText());
        return Identifier.toIdentifier(newName);
    }
}
