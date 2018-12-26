package com.example.yusriyusron.matchscheduler.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Match (
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("idHomeTeam")
    var idHomeTeam: String? = null,

    @SerializedName("idAwayTeam")
    var idAwayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("intHomeShots")
    var homeShots: String? = null,

    @SerializedName("intAwayShots")
    var awayShots: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCard: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCard: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCard: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCard: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalkeeper: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMidfield: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayForward: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalDetail: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalDetail: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubstitutes: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubstitutes: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventId)
        parcel.writeString(eventName)
        parcel.writeString(dateEvent)
        parcel.writeString(homeTeam)
        parcel.writeString(awayTeam)
        parcel.writeString(idHomeTeam)
        parcel.writeString(idAwayTeam)
        parcel.writeString(homeScore)
        parcel.writeString(awayScore)
        parcel.writeString(homeShots)
        parcel.writeString(awayShots)
        parcel.writeString(homeRedCard)
        parcel.writeString(awayRedCard)
        parcel.writeString(homeYellowCard)
        parcel.writeString(awayYellowCard)
        parcel.writeString(homeGoalkeeper)
        parcel.writeString(awayGoalkeeper)
        parcel.writeString(homeDefense)
        parcel.writeString(awayDefense)
        parcel.writeString(homeMidfield)
        parcel.writeString(awayMidfield)
        parcel.writeString(homeForward)
        parcel.writeString(awayForward)
        parcel.writeString(homeGoalDetail)
        parcel.writeString(awayGoalDetail)
        parcel.writeString(homeSubstitutes)
        parcel.writeString(awaySubstitutes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Match> {
        override fun createFromParcel(parcel: Parcel): Match {
            return Match(parcel)
        }

        override fun newArray(size: Int): Array<Match?> {
            return arrayOfNulls(size)
        }
    }
}