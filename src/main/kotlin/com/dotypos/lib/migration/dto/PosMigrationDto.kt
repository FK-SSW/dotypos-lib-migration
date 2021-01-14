@file:UseSerializers(
    BigDecimalSerializer::class,
    DateSerializer::class
)

package com.dotypos.lib.migration.dto

import com.dotypos.lib.migration.dto.config.PosConfigurationDto
import com.dotypos.lib.migration.dto.entity.*
import com.dotypos.lib.migration.serialization.BigDecimalSerializer
import com.dotypos.lib.migration.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.valiktor.functions.*
import org.valiktor.validate
import java.util.*

@Serializable
data class PosMigrationDto(
    val metadata: Metadata,

    /**
     * Configuration of migrated POS
     */
    @SerialName("posConfiguration")
    val posConfiguration: PosConfigurationDto,

    /**
     * Employees
     */
    @SerialName("employees")
    val employees: Set<EmployeeMigrationDto>,

    /**
     * Sellers
     */
    @SerialName("sellers")
    val sellers: Set<SellerMigrationDto>,

    /**
     * Sorted list of meal courses
     */
    @SerialName("courses")
    val courses: List<CourseMigrationDto>,

    /**
     * Sorted list of sale categories
     */
    @SerialName("categories")
    val categories: List<CategoryMigrationDto>,

    /**
     * Sorted list of products - order is also preserved in categories
     */
    @SerialName("products")
    val products: List<ProductMigrationDto>,

    /**
     * List of product ingredients used for stock deduction
     */
    @SerialName("ingredients")
    val ingredients: List<ProductIngredientMigrationDto>,

    /**
     * List of customer discount groups definitions
     */
    @SerialName("customerDiscountGroups")
    val customerDiscountGroups: List<CustomerDiscountGroupMigrationDto>,

    /**
     * List of customers
     */
    @SerialName("customers")
    val customers: List<CustomerMigrationDto>,

    /**
     * List of table pages
     */
    @SerialName("tablePages")
    val tablePages: List<TablePageMigrationDto>,

    /**
     * List of tables
     */
    @SerialName("tables")
    val tables: List<TableMigrationDto>,

    /**
     * List of printers and print tasks
     */
    @SerialName("printers")
    val printers: List<PrinterMigrationDto>,
) {

    init {
        validate(this) {
            // TODO: Validate all entities for unique ID
            // TODO: Validate all related entities for related entity existence
            // TODO: Validate overlap of tables
        }
    }

    @Serializable
    data class Metadata(
        /**
         * Unique identification of migration file starting with migration prefix string
         */
        @SerialName("migrationId")
        val migrationId: String,

        /**
         * Date of migration file creation
         */
        @SerialName("created")
        val created: Date,

        /**
         * Registration email of user (Administrator) - used to prefill in Dotypos Setup Guide
         */
        @SerialName("email")
        val email: String?,

        /**
         * License key to be used for migration
         */
        @SerialName("licenseKey")
        val licenseKey: String?,

        /**
         * Source POS metadata
         */
        @SerialName("pos")
        val pos: PosMetadata,
    ) {
        init {
            validate(this) {
                validate(Metadata::migrationId).hasSize(min = 10)
                validate(Metadata::created).isLessThan(Date())
            }
        }

    }

    @Serializable
    data class PosMetadata(
        /**
         * Foreign POS id - used for subsequent migrations
         * Markeeta: shopId/terminalId
         */
        @SerialName("id")
        val id: String
    ) {
        init {
            validate(this) {
                validate(PosMetadata::id).isNotBlank()
            }
        }
    }
}