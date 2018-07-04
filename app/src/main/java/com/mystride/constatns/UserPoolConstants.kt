package com.mystride.constatns

import com.amazonaws.regions.Regions

object UserPoolConstants {


    const val USER_ID_REGEX = "/^user_id-[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}\$/i"

    /**
     * Add your pool id here
     */
    const val userPoolId = "us-east-2_2xB8rPDqd"

    /**
     * Add you app id
     */
    const val clientId = "7f37607uhe8j2ko2qvr9isqcto"

    /**
     * App secret associated with your app id - if the App id does not have an associated App secret,
     * set the App secret to null.
     * e.g. clientSecret = null;
     */
    const val clientSecret: String = "16f84qut72aqj1k42kmg6t4qbapaotu8pq0t0h0mho5c5emjsvfm"

    /**
     * Set Your User Pools region.
     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
     */
    val cognitoRegion = Regions.US_EAST_2

    //coginto user attributes keys
    const val COGINTO_USER_ATTRIBUTES_EMAIL = "email"
    const val COGINTO_USER_ATTRIBUTES_PHONE_NUMBER = "phone_number"
    const val COGINTO_USER_ATTRIBUTES_GIVEN_NAME = "given_name"
    const val COGINTO_USER_ATTRIBUTES_FAMILY_NAME = "family_name"
    const val COGINTO_USER_ATTRIBUTES_LOCALE = "locale"


    //default Locale
    const val LOCALE_US = "US"

    const val USER_ID_PREFIX = "user_id-"

    const val ALIAS_EXISTS_EXCEPTION = "AliasExistsException"
    const val LIMIT_EXCEEDED_EXCEPTION = "LimitExceededException"
    const val CODE_MISMATCH_EXCEPTION = "CodeMismatchException"
    const val GENERAL_EXCEPTION = "GeneralException"

}