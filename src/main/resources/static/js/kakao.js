window.kakao = window.kakao || {};
window.kakao.maps = window.kakao.maps || {};
window.daum && window.daum.maps ? window.kakao.maps = window.daum.maps : (window.daum = window.daum || {}, window.daum.maps = window.kakao.maps);

(function() {
    var e = window.kakao.maps;
    if (e.readyState === 2) return;
    if (void 0 === e.readyState) {
        e.onloadcallbacks = [];
        e.readyState = 0;
    }
    
    e.load = function(callback) {
        if (e.readyState === 2) {
            callback();
        } else {
            e.onloadcallbacks.push(callback);
        }
    };
    
    function triggerLoad() {
        e.readyState = 2;
        while (e.onloadcallbacks.length > 0) {
            var cb = e.onloadcallbacks.shift();
            try { cb(); } catch(err) { console.error(err); }
        }
    }

    e.LatLng = function(lat, lng) { this.lat = lat; this.lng = lng; };
    e.LatLng.prototype.getLat = function() { return this.lat; };
    e.LatLng.prototype.getLng = function() { return this.lng; };

    e.Size = function(width, height) { this.width = width; this.height = height; };
    e.Point = function(x, y) { this.x = x; this.y = y; };
    e.MarkerImage = function(src, size, options) { this.src = src; };

    e.Marker = function(options) { this.options = options; };
    e.Marker.prototype.setMap = function(map) {};
    e.Marker.prototype.getPosition = function() { return new e.LatLng(37.4954, 127.0333); };

    e.Map = function(container, options) {
        container.innerHTML = '<div style="display:flex;align-items:center;justify-content:center;height:100%;background:#f8f9fa;color:#6c757d;font-family:sans-serif;text-align:center;padding:20px;"><div><h3>🗺️ 네트워크 방화벽 안내</h3><p>현재 서버 환경에서 외부 카카오 CDN 통신이 차단되어 지도가 가상 모드로 동작합니다.</p><p style="font-size:12px;color:#adb5bd;">(좌측 대피소 목록과 데이터 필터링은 정상 작동합니다.)</p></div></div>';
    };
    e.Map.prototype.setCenter = function() {};
    e.Map.prototype.setLevel = function() {};
    e.Map.prototype.getCenter = function() { return new e.LatLng(37.4954, 127.0333); };
    e.Map.prototype.getBounds = function() {
        return {
            getSouthWest: function() { return new e.LatLng(37.4, 127.0); },
            getNorthEast: function() { return new e.LatLng(37.6, 127.2); }
        };
    };
    e.Map.prototype.relayout = function() {};
    e.Map.prototype.panTo = function() {};

    e.services = {
        Status: { OK: "OK", ZERO_RESULT: "ZERO_RESULT", ERROR: "ERROR" },
        Places: function() {},
        Geocoder: function() {}
    };
    e.services.Places.prototype.keywordSearch = function(keyword, callback) { callback([], "ZERO_RESULT"); };
    e.services.Geocoder.prototype.addressSearch = function(addr, callback) { callback([], "ZERO_RESULT"); };

    e.event = {
        addListener: function() {}
    };

    setTimeout(triggerLoad, 50);
})();
