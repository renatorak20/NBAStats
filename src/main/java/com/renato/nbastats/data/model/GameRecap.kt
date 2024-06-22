package com.renato.nbastats.data.model

import java.io.Serializable

data class GameRecap(
    val dateEpoch: Long?,
    val recap: RecapData?
): Serializable

data class RecapData(
    val recapData: RecapDetails?
): Serializable

data class RecapDetails(
    val heroConfiguration: HeroConfiguration?,
    val gameId: String?,
    val seasonYear: String?,
    val seasonType: String?,
    val period: Int?,
    val homeTeam: Team?,
    val awayTeam: Team?,
    val seasonLeadersFlag: Int?,
    val gameTimeEastern: String?,
    val gameTimeUtc: String?,
    val actions: List<Action>?,
    val images: ImageSizes?,
    val shareUrl: String?
): Serializable

data class HeroConfiguration(
    val gameRecap: GameRecapInfo?
): Serializable

data class GameRecapInfo(
    val slug: String?,
    val title: String?,
    val subTitle: String?,
    val image: Image?
): Serializable

data class Image(
    val imageUrl: String?
): Serializable

data class Team(
    val teamId: Int?,
    val teamName: String?,
    val wins: Int?,
    val losses: Int?,
    val teamSubtitle: String?,
    val score: Int?,
    val timeoutsRemaining: Int?,
    val inBonus: Boolean?,
    val teamTricode: String?,
    val specialInfoPrefix: String?,
    val marqueePlayer: String?,
    val periods: List<PeriodScore>?,
    val teamLeader: TeamLeader?
): Serializable

data class PeriodScore(
    val period: Int?,
    val score: Int?
): Serializable

data class TeamLeader(
    val personId: Int?,
    val name: String?,
    val jerseyNum: String?,
    val position: String?,
    val teamTricode: String?,
    val playerSlug: String?,
    val points: String?,
    val rebounds: String?,
    val assists: String?,
    val blocks: String?,
    val steals: String?
): Serializable

data class Action(
    val title: String?,
    val resourceLocator: ResourceLocator?
): Serializable

data class ResourceLocator(
    val resourceUrl: String?,
    val resourceId: String?,
    val resourceType: String?
): Serializable

data class ImageSizes(
    val size1920x1080: String?,
    val size1280x720: String?,
    val size640x360: String?,
    val size480x270: String?,
    val size320x180: String?,
    val size160x90: String?
): Serializable

