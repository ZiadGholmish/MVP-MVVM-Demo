package com.mystride.constatns

import com.amazonaws.regions.Regions

object UserPoolConstants{


    const val USER_ID_REGEX = "/^user_id-[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}\$/i"

    /**
     * Add your pool id here
     */
    const val userPoolId = "us-east-2_3vd4uj5Wa"

    /**
     * Add you app id
     */
    const val clientId = "2gj4knek0rp98jsu2j1anqei4k"

    /**
     * App secret associated with your app id - if the App id does not have an associated App secret,
     * set the App secret to null.
     * e.g. clientSecret = null;
     */
    const val clientSecret: String = "ftg19m5q3al33r2kgc104d5ctit7ds8n0trt4v4gapak2kalm76"

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
}