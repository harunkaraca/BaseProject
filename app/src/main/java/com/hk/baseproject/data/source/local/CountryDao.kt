package com.hk.baseproject.data.source.local

import androidx.room.*

@Dao
interface CountryDao {
    /**
     * Select all country from the country table.
     *
     * @return all country.
     */
    @Query("Select * from COUNTRY")
    suspend fun getCountryList():List<CountryT>

    /**
     * Select a country by id.
     *
     * @param countryId the country id.
     * @return the country with countryId.
     */
    @Query("SELECT * FROM COUNTRY WHERE id = :id")
    suspend fun getCountryById(id: Int): CountryT?

    /**
     * Insert a country in the database. If the country already exists, replace it.
     *
     * @param countryT the country to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryT: CountryT)

    /**
     * Update a country.
     *
     * @param countryT countryT to be updated
     * @return the number of country updated. This should always be 1.
     */
    @Update
    suspend fun updateCountry(countryT: CountryT):Int

    /**
     * Delete a country by id.
     *
     * @return the number of country deleted. This should always be 1.
     */
    @Query("DELETE FROM country WHERE id = :id")
    suspend fun deleteCountryById(id: Int): Int

    /**
     * Delete all country.
     */
    @Query("DELETE FROM country")
    suspend fun deleteAllCountry()
}