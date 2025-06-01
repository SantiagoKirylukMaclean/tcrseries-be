package com.puetsnao.tcrseries.domain.model

/**
 * Represents the features/accesses available in the system.
 */
enum class Accesses {
    /**
     * Access to standings information.
     */
    STANDINGS,
    
    /**
     * Access to simulate weekend functionality.
     */
    SIMULATE_WEEKEND,
    
    /**
     * Access to timing by microsector functionality.
     */
    TIMING_BY_MICROSECTOR,
    
    /**
     * Access to test configuration functionality.
     */
    TEST_CONFIGURATION,
    
    /**
     * Access to parts list functionality.
     */
    PARTS_LIST,
    
    /**
     * Access to notes functionality.
     */
    NOTES
}