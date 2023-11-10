package com.easyhz.placeapp.ui.component.map

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.easyhz.placeapp.BuildConfig
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    context: Context
) {
    NaverMapSdk.getInstance(LocalContext.current).client =
        NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_SDK_KEY)
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    // Lifecycle 이벤트 적용
    // recomposition 시에도 유지 되어야 함.
    val mapView = remember { getMapView(context = context) }
    val lifecycleObserver = remember { getLifecycleEventObserverOfMap(mapView = mapView, coroutineScope = coroutineScope) }

    // 뷰 해제시 이벤트 리스너 제거
    DisposableEffect(true) {
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.clip(RoundedCornerShape(15.dp))
    )
}

private fun getMapView(context: Context) = MapView(context)
    .apply {
        getMapAsync { naverMap ->
            val marker = Marker()
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.521715859, 126.924290018))
            naverMap.moveCamera(cameraUpdate)
            naverMap.isNightModeEnabled = true
            naverMap.isIndoorEnabled = true
            marker.position = LatLng(37.521715859, 126.924290018)
            marker.map = naverMap

        }
    }


private fun getLifecycleEventObserverOfMap(
    coroutineScope: CoroutineScope,
    mapView: MapView
) = LifecycleEventObserver { source, event ->
    coroutineScope.launch {
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> { }
        }
    }
}
