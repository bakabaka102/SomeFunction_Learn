import java.io.File

class GitInfo(
    private val parentDir: File
) {

    val branch: String? by lazy {
        process(GIT_COMMAND_GET_BRANCH_NAME)?.inputStream?.bufferedReader()?.readLine()?.toString()?.trim()
    }

    private fun process(command: String): Process? = Runtime.getRuntime().exec(
        command,
        emptyArray(),
        parentDir
    )

    val branchShort: String? by lazy {
        val withoutPrefix = if (branch?.contains("/") == true) {
            branch?.dropWhile { it != '/' }?.drop(1)
        } else {
            branch
        }

        withoutPrefix?.take(10)
    }

    val hash: String? by lazy {
        process(GIT_COMMAND_GET_COMMIT_HASH)?.inputStream?.bufferedReader()?.readLine()?.trim()
    }

    val tag: String? by lazy {
        process(GIT_COMMAND_GET_TAG)?.inputStream?.bufferedReader()?.readLine()?.trim()
    }

    val isMaster: Boolean
        get() = branch == BRANCH_MASTER

    val isRelease: Boolean
        get() = tag?.matches(REGEX_RELEASE) == true

    val isReleaseCandidate: Boolean
        get() = tag?.matches(REGEX_RELEASE_CANDIDATE) == true

    val releaseCandidateSuffix: String?
        get() = tag?.let { REGEX_RELEASE_CANDIDATE_SUFFIX.find(it)?.value?.drop(1) }

    val isHead: Boolean
        get() = branch == HEAD

    companion object {
        const val HEAD = "HEAD"

        const val BRANCH_MASTER = "master"

        const val GIT_COMMAND_GET_TAG = "git describe --tags --exact-match HEAD"

        const val GIT_COMMAND_GET_BRANCH_NAME = "git rev-parse --abbrev-ref HEAD"

        const val GIT_COMMAND_GET_COMMIT_HASH = "git rev-parse --short HEAD"

        val REGEX_RELEASE = Regex("""^v\d+\.\d+\.\d+$""")

        val REGEX_RELEASE_CANDIDATE = Regex("""^v\d+\.\d+\.\d+-rc\d+$""")

        val REGEX_RELEASE_CANDIDATE_SUFFIX = Regex("""-rc\d+$""")
    }

}