package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.api.FeedService
import com.easyhz.placeapp.di.CommonModule.ProvideGson
import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.comment.Comment
import com.easyhz.placeapp.domain.model.feed.comment.write.PostComment
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.gallery.Gallery
import com.easyhz.placeapp.domain.model.post.ModifyPost
import com.easyhz.placeapp.domain.model.post.Post
import com.easyhz.placeapp.domain.model.user.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class FeedRepositoryImpl
@Inject constructor(
    private val feedService: FeedService,
    @ProvideGson private val gson: Gson
):FeedRepository {
    override suspend fun fetchFeedContents(page: Int, userId: String): Response<Feed> = feedService.getFeed(page, userId)

    override suspend fun fetchFeedDetail(id: Int, userId: String): Response<FeedDetail> = feedService.getFeedDetail(id = id, userId = userId)

    override suspend fun fetchComments(id: Int, page: Int, userId: String): Response<Comment> = feedService.getComments(id = id, page = page, userId = userId)
    override suspend fun writePost(
        post: Post,
        images: List<Gallery>,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val files: List<MultipartBody.Part> = images.map {createMultipartBody(File(it.path)) }
        val content = createRequestBody(post)

        val response = feedService.writePost(content, files)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    override suspend fun deletePost(
        id: Int,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = feedService.deletePost(id)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    override suspend fun modifyPost(
        id: Int,
        content: ModifyPost,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = feedService.modifyPost(id, content)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    override suspend fun writeComment(
        id: Int,
        comment: PostComment,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = feedService.writeComments(id = id, comment =  comment)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    override suspend fun savePost(
        boardId: Int,
        user: User,
        onComplete: (Boolean) -> Unit
    )  {
        saveAction(boardId, user, feedService::savePost, onComplete)
    }

    override suspend fun savePlace(
        boardId: Int,
        user: User,
        onComplete: (Boolean) -> Unit
    ) {
        saveAction(boardId, user, feedService::savePlace, onComplete)
    }

    private suspend fun saveAction(
        boardId: Int,
        user: User,
        saveFunction: suspend (Int, User) -> Response<Void>,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = saveFunction(boardId, user)
        onComplete(response.isSuccessful)
    }

    private fun createMultipartBody(image: File): MultipartBody.Part {
        val requestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("files", image.name, requestBody)
    }

    private fun createRequestBody(item: Any): RequestBody {
        return gson.toJson(item).toRequestBody("application/json".toMediaTypeOrNull())
    }
}