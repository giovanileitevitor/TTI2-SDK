package com.timwe.tti2sdk.data.entity

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class BoosterMissions(
    var titleBooster: String,
    var boosterMissions: List<Mission2>
)

data class AdventureMissions(
    var titleAdventure: String,
    var adventureMissions: List<Mission2>,
    var skipEnabled: Boolean? = false,
    var groupId: Long
)

data class DailyMissions(
    var titleMissions: String,
    var dailyMissions: List<Mission2>
)

data class Mission(
    var id: Long,
    var type: String,
    var flagColor: String,
    var flagText: String,
    var extraFlagColor: String,
    var extraFlagText: String,
    var distance: Long,
    var distanceUnit: String,
    var title: String,
    var subtitle: String,
    var extraInfo: String,
    var qtdItens: String,
    var status: String
)

data class Mission2 (
    val missionId: Long,
    val missionType: String,
    val missionSubType: String? = null,
    val groupMissionId: Long,
    val name: String,
    val type: String? = null,
    val description: String,
    val category: String,
    val imageUrl: String,
    val status: String,
    val rewards: List<Reward>,
    val currentValue: Long,
    val threshold: Long,
    val eventType: String? = null,
    val eventValue: String? = null,
    val order: Long,
    val quiz: Quiz?,
    val educationalCards: EducationalCards,
    val additionalProperties: MissionAdditionalProperties,
    val skipAllowed: Boolean
)

data class MissionGroups(
    val newMessages: Boolean? = false,
    val userChangedCity: Boolean? = false,
    val missionGroup: List<MissionGroup>,
//    val groups: Map<String, Group>
)

data class MissionGroup(
    val groupId: Long,
    val name: String,
    val imageUrl: String? = null,
    val description: String,
    val shortDescription: String? = null,
    val groupType: String,
    val currentValue: String? = null,
    val threshold: String? = null,
    val order: Int,
    val skipAllowed: Boolean,
    @SerializedName("missions")
    val missions: List<Mission2>,
    val additionalProperties: GroupAdditionalProperties
)

typealias GroupAdditionalProperties = JsonObject

data class Group(
    val groupID: Long,
    val name: String,
    val imageURL: Any? = null,
    val description: String,
    val shortDescription: Any? = null,
    val groupType: String,
    val missions: List<Mission2>,
    val additionalProperties: GroupAdditionalProperties
)

data class MissionAdditionalProperties (
    @SerializedName("mission_menu_title")
    val missionMenuTitle: String,
    @SerializedName("mission_type")
    val missionType: String,
    @SerializedName("mission_menu_description")
    val missionMenuDescription: String,
    @SerializedName("mission_action_url1")
    val missionActionUrl1: String,
    @SerializedName("mission_action_url2")
    val missionActionUrl2: String,
    @SerializedName("mission_action_threshold")
    val missionActionThreshold: Int
)

data class EducationalCards(
    @SerializedName("educationalCards")
    val educationalCards: List<EducationalCard>
)

data class EducationalCard(
    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String
)

data class Reward (
    val prizeName: String,
    val description: String,
    val prizeValue: String,
    val value: Long,
    val imageUrl: String,
    val label: String
)

data class Skip(
    val skipped: Boolean,
    val status: String
)

data class Quiz(
    @SerializedName("totalQuestions")
    val totalQuestions: Int,
    @SerializedName("questions")
    val questions: List<Question>,
    @SerializedName("survey")
    val survey: Boolean
)

data class Question(
    val id: Int,
    val questionId: Long,
    val title: String,
    val topic: String,
    val question: String,
    val imageUrl: String,
    val answers: List<Answer>
)

data class Answer(
    val answer: String,
    val dropDownList: String? = null,
    val correctAnswer: Boolean,
    val dropDownAnswer: Boolean
)

fun generateQuiz(): Quiz {
    return Quiz(
        totalQuestions = 3,
        questions = generateQuestions(),
        survey = false
    )
}

fun generateQuestions(): List<Question>{
    val questions = arrayListOf<Question>()

    questions.add(
        Question(
            id = 1,
            questionId = 1.toLong(),
            title = "Question number 1 ? ",
            topic = "",
            question = "question number - param question ",
            imageUrl = "https://vulcan.condenastdigital.com/demo/photos/6430305a43557ca5c77bec32/master/pass/WIR_LIFT_Sale_Growler_Paywall_Bar_400x400-update-white-bg_1x%20(1).png?format=original",
            answers = generateAnswers()
        )
    )

    questions.add(
        Question(
            id = 2,
            questionId = 2.toLong(),
            title = "Question number 2 ? ",
            topic = "",
            question = "question number - param question ",
            imageUrl = "https://vulcan.condenastdigital.com/demo/photos/6430305a43557ca5c77bec32/master/pass/WIR_LIFT_Sale_Growler_Paywall_Bar_400x400-update-white-bg_1x%20(1).png?format=original",
            answers = generateAnswers()
        )
    )

    questions.add(
        Question(
            id = 3,
            questionId = 3.toLong(),
            title = "Question number 3 ? ",
            topic = "",
            question = "question number - param question ",
            imageUrl = "https://vulcan.condenastdigital.com/demo/photos/6430305a43557ca5c77bec32/master/pass/WIR_LIFT_Sale_Growler_Paywall_Bar_400x400-update-white-bg_1x%20(1).png?format=original",
            answers = generateAnswers()
        )
    )

    return questions
}

fun generateAnswers(): List<Answer>{
    val answers = arrayListOf<Answer>()

    answers.add(
        Answer(
            answer = "Answer 1",
            dropDownList = " - ",
            correctAnswer = false,
            dropDownAnswer = false
        )
    )

    answers.add(
        Answer(
            answer = "Answer 2",
            dropDownList = " - ",
            correctAnswer = true,
            dropDownAnswer = false
        )
    )

    answers.add(
        Answer(
            answer = "Answer 3",
            dropDownList = " - ",
            correctAnswer = false,
            dropDownAnswer = false
        )
    )

    return answers
}