package com.easyhz.placeapp.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.component.comment.Comments
import com.easyhz.placeapp.ui.home.feed.FeedType
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun BoardDetail(id: Int) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dummy = FeedType(
            id = 1,
            imagePath = "https://picsum.photos/id/307/200/300",
            userName = "유저 1",
            regDate = "2023.10.29",
            placeName= "대한민국, 제주특별자치도",
            bookmarkCount = 5,
            content = "제가 이번 추석 연후에 연차까지 내서 빈대? 인정 나도 알아 뉴스는 안봄 하지만 근데 우리 강동구는 괜찮은데 지하철이 개무서움\n" +
                    "자리에 앉기무서운데 그래도 앉아\n" +
                    "현생이 힘드니까",
            detailPlace = "제주 흑돼지"
        )
    val mock = arrayOf(
        CommentType(
            id = 1,
            userName = "유저1",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 2,
            userName = "유저2",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 3,
            userName = "유저3",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 4,
            userName = "유저4",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 5,
            userName = "유저5",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 6,
            userName = "유저6",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        ContentCard(
            item = dummy,
            imageSize = (screenWidth - 100).dp,
            cardWidth = screenWidth.dp,
            modifier = Modifier
                .width(screenWidth.dp)
                .padding(CONTENT_ALL.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(PlaceAppTheme.colorScheme.mainBackground),
            onMapClick = { onMapClick() }
        )
        Comments(
            items = mock,
            modifier = Modifier
                .width(screenWidth.dp)
                .padding(CONTENT_ALL.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(PlaceAppTheme.colorScheme.mainBackground),
        )
    }

}

data class CommentType (
    val id: Int,
    val userName: String,
    val content: String,
    val regDate: String
)

private fun onMapClick() {

}

@Preview("default")
@Preview("DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FeedPreview() {
    PlaceAppTheme {
        BoardDetail(
            id = 1
        )
    }
}