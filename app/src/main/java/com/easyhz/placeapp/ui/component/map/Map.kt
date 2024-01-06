package com.easyhz.placeapp.ui.component.map

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.easyhz.placeapp.BuildConfig
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.CameraUpdateParams
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    places: List<LatLngType>
) {
    NaverMapSdk.getInstance(LocalContext.current).client =
        NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_SDK_KEY)
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Lifecycle 이벤트 적용
    // recomposition 시에도 유지 되어야 함.
    val mapView = remember { MapView(context) }
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
        modifier = modifier
    ) {
        setMap(it, places)
    }

}

data class LatLngType(
    val latitude: Double,
    val longitude: Double
)

private fun setMap(mapView: MapView, places: List<LatLngType>) {
    mapView.getMapAsync { naverMap ->
        val cameraUpdate = setCamera(places)

        setMarkers(naverMap, places)

        naverMap.apply {
            moveCamera(cameraUpdate)
            isIndoorEnabled = true
        }
    }
}

const val ALL_PLACE_ZOOM_SIZE = 13.0
const val ONE_PLACE_ZOOM_SIZE = 15.5

private fun setCamera(places: List<LatLngType>): CameraUpdate {
    val zoom = if (places.size > 1) ALL_PLACE_ZOOM_SIZE else ONE_PLACE_ZOOM_SIZE
    val cameraParams = CameraUpdateParams().apply {
        zoomTo(zoom)
        scrollTo(LatLng(places.first().latitude, places.first().longitude))
    }

    return CameraUpdate.withParams(cameraParams)
}

/**
 * TODO: 캡션 상의
 */
private fun setMarkers(naverMap: NaverMap, places: List<LatLngType>) {
    places.map { place ->
        Marker().apply {
            position = LatLng(place.latitude, place.longitude)
            if (places.size > 1) {
                isHideCollidedMarkers = true
            }
            map = naverMap
        }
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
