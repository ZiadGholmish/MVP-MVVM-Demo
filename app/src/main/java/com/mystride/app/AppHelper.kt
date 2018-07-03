package com.mystride.app

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions

object AppHelper {

    // Change the next three lines of code to run this demo on your user pool

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
    val cognitoRegion = Regions.DEFAULT_REGION

}