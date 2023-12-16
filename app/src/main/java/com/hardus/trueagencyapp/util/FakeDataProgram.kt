package com.hardus.trueagencyapp.util

object LocalProgramDataProvider {

    val defaultProgram = generateFakeDataProgram()[0]

    fun generateFakeDataProgram(): List<Post> {

        val post1 = Post(
            idTraining = "1",
            headerTitle = "Training SM7",
            introParagraph = "Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.Lorem ipsum dolor sit amet consectetur.",
            imagePost = "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y2FmZXxlbnwwfDB8MHx8fDA%3D",
            subTrainingList = listOf(
                SubTraining(
                    idSubTraining = "1",
                    bodyTitle = "True Talk",
                    day = "Rabu",
                    image = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?q=80&w=2047&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                    paragraph = "Lorem ipsum 1"
                ),
                SubTraining(
                    idSubTraining = "2",
                    bodyTitle = "True Story",
                    day = "Kamis",
                    image = "https://images.unsplash.com/photo-1559925393-8be0ec4767c8?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y2FmZXxlbnwwfDB8MHx8fDA%3D",
                    paragraph = "Lorem ipsum 2"
                ),
                SubTraining(
                    idSubTraining = "3",
                    bodyTitle = "True Gathering",
                    day = "Sabtu",
                    image = "https://images.unsplash.com/photo-1445116572660-236099ec97a0?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8Y2FmZXxlbnwwfDB8MHx8fDA%3D",
                    paragraph = "Lorem ipsum 3"
                )
            )
        )


        val post2 = Post(
            idTraining = "2",
            headerTitle = "Second Training",
            introParagraph = "Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.",
            imagePost = null,
            subTrainingList = listOf(
                SubTraining(
                    idSubTraining = "4",
                    bodyTitle = "Sub Training 4",
                    day = "Tuesday",
                    image = null,
                    paragraph = "Lorem ipsum 4"
                ),
                SubTraining(
                    idSubTraining = "5",
                    bodyTitle = "Sub Training 5",
                    day = "Thursday",
                    image = null,
                    paragraph = "Lorem ipsum 5"
                ),
                SubTraining(
                    idSubTraining = "6",
                    bodyTitle = "Sub Training 6",
                    day = "Saturday",
                    image = null,
                    paragraph = "Lorem ipsum 6"
                ),
                SubTraining(
                    idSubTraining = "6",
                    bodyTitle = "Sub Training 6",
                    day = "Saturday",
                    image = null,
                    paragraph = "Lorem ipsum 6"
                )
            )
        )

        val post3 = Post(
            idTraining = "3",
            headerTitle = "Third Training",
            introParagraph = "Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.",
            imagePost = "https://images.unsplash.com/photo-1615322958568-7928d3291f7a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fGNhZmV8ZW58MHwwfDB8fHww",
            subTrainingList = listOf(
                SubTraining(
                    idSubTraining = "7",
                    bodyTitle = "Sub Training 7",
                    day = "Monday",
                    image = null,
                    paragraph = "Lorem ipsum 7"
                ),
                SubTraining(
                    idSubTraining = "8",
                    bodyTitle = "Sub Training 8",
                    day = "Wednesday",
                    image = null,
                    paragraph = "Lorem ipsum 8"
                ),
                SubTraining(
                    idSubTraining = "9",
                    bodyTitle = "Sub Training 9",
                    day = "Friday",
                    image = null,
                    paragraph = "Lorem ipsum 9"
                ),
                SubTraining(
                    idSubTraining = "9",
                    bodyTitle = "Sub Training 9",
                    day = "Friday",
                    image = null,
                    paragraph = "Lorem ipsum 9"
                ),
                SubTraining(
                    idSubTraining = "9",
                    bodyTitle = "Sub Training 9",
                    day = "Friday",
                    image = null,
                    paragraph = "Lorem ipsum 9"
                )
            )
        )

        val post4 = Post(
            idTraining = "4",
            headerTitle = "Fourth Training",
            introParagraph = "Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.",
            imagePost = "https://images.unsplash.com/photo-1600093463592-8e36ae95ef56?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fGNhZmV8ZW58MHwwfDB8fHww",
            subTrainingList = listOf(
                SubTraining(
                    idSubTraining = "10",
                    bodyTitle = "Sub Training 10",
                    day = "Monday",
                    image = null,
                    paragraph = "Lorem ipsum 10"
                ),
            )
        )

        val post5 = Post(
            idTraining = "5",
            headerTitle = "Fifth Training",
            introParagraph = "Lorem ipsum dolor sit amet consectetur. Cursus nisl odio leo dictum aliquam. Scelerisque non nam purus sed vulputate mi scelerisque suspendisse quis.",
            imagePost = "https://images.unsplash.com/photo-1510074377623-8cf13fb86c08?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjR8fGNvbXBhbnl8ZW58MHwwfDB8fHww",
            subTrainingList = listOf(
                SubTraining(
                    idSubTraining = "13",
                    bodyTitle = "Sub Training 13",
                    day = "Monday",
                    image = null,
                    paragraph = "Lorem ipsum 13"
                ),
                SubTraining(
                    idSubTraining = "14",
                    bodyTitle = "Sub Training 14",
                    day = "Wednesday",
                    image = null,
                    paragraph = "Lorem ipsum 14"
                )
            )
        )

        return listOf(post1, post2, post3, post4, post5)
        //return Post()
    }
}