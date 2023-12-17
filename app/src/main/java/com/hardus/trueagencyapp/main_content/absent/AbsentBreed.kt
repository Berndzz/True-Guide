package com.hardus.trueagencyapp.main_content.absent

import kotlinx.coroutines.flow.flow

data class AbsentBreed(
    val idAbsent: String? = null,
    val name: String = "Q1",
    val description: String? = null,
    val subAbsentList: List<SubAbsent>
)


data class SubAbsent(
    //tambahkan idUser
    val idUser: String? = null,
    val idSubAbsent: String,
    val headerTitle: String,
    val bodyTitle: String,
    val date: String,
    val location: String,
    val present: Boolean? = null,
)

fun getAbsentBreed() = flow {
    val absentBreed = listOf(
        AbsentBreed(
            idAbsent = "1",
            name = "Q1",
            description = "Description Q1",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-01",
                    location = "Kantor True Agency",
                    present = true
                ),
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-02",
                    location = "Kantor True Agency",
                    present = false
                ),
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-02",
                    location = "Kantor True Agency",
                    present = false
                ),
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-02",
                    location = "Kantor True Agency",
                    present = false
                ),
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-02",
                    location = "Kantor True Agency",
                    present = true
                ),
                SubAbsent(
                    idSubAbsent = "1",
                    headerTitle = "Header 1",
                    bodyTitle = "Body 1",
                    date = "2023-01-02",
                    location = "Kantor True Agency",
                    present = false
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "2",
            name = "Q2",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "2",
                    headerTitle = "Header 2",
                    bodyTitle = "Body 2",
                    date = "2023-01-03",
                    location = "Kantor True Agency",
                    present = true
                ),
                // Add more sub absent items as needed
            )
        ),
        AbsentBreed(
            idAbsent = "3",
            name = "Q3",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "3",
                    headerTitle = "Header 3",
                    bodyTitle = "Body 3",
                    date = "2023-01-03",
                    location = "Kantor True Agency",
                    present = true
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "4",
            name = "Material",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "4",
                    headerTitle = "Header 4",
                    bodyTitle = "Body 4",
                    date = "2023-01-04",
                    location = "Kantor True Agency",
                    present = true
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "5",
            name = "PSA",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "5",
                    headerTitle = "Header 5",
                    bodyTitle = "Body 5",
                    date = "2023-01-05",
                    location = "Kantor True Agency",
                    present = true
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "6",
            name = "PRU Sales Academy",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "3",
                    headerTitle = "Header 3",
                    bodyTitle = "Body 3",
                    date = "2023-01-06",
                    location = "Kantor True Agency",
                    present = false
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "7",
            name = "Certificate & Knowledge Academy",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "7",
                    headerTitle = "Header 7",
                    bodyTitle = "Body 7",
                    date = "2023-01-07",
                    location = "Kantor True Agency",
                    present = false
                ),
            )
        ),
        AbsentBreed(
            idAbsent = "8",
            name = "Sales Skill & Product",
            subAbsentList = listOf(
                SubAbsent(
                    idSubAbsent = "8",
                    headerTitle = "Header 8",
                    bodyTitle = "Body 8",
                    date = "2023-01-08",
                    location = "Kantor True Agency",
                    present = false
                ),
            )
        ),
    )
    emit(absentBreed)
}