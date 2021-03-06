var scrollList = [];
var markers = [];
var gelocationCheck = false;
// 지도를 생성합니다
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.505754, 127.040938), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption);

$(document).ready(function () {
    // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
    if (navigator.geolocation) {
        gelocationCheck = true;
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {

            var lat = position.coords.latitude; // 위도
            var lon = position.coords.longitude; // 경도

            var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                message = '<div style="padding:5px;">당신의 위치</div>'; // 인포윈도우에 표시될 내용입니다

            // 마커와 인포윈도우를 표시합니다
            displayMarker(locPosition, message);

        });

    } else { // HTML5의 GeoLocation을 사용할 수 없을때
        alert("GeoLocation 사용못함.");
    }
    document.// 지도 중심좌표를 얻어옵니다
        var
    latlng = map.getCenter();
    $.ajax({
        type: "GET",
        data: {"Lat": 37.505754, "Lng": 127.040938},
        url: "/hospitaApi",
        dataType: "json",
        success: function (data) {
            console.log(data);
            mapApi(data);
        },
        error: function (error) {
            console.log(error);
        }
    })

})



//지도에 마커와 인포윈도우를 표시하는 함수
function displayMarker(locPosition, message) {
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition
    });

    var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable
    });

    // 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);

    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);
}

// 마우스 드래그로 지도 이동이 완료되었을 때 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'dragend', function () {

    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();
    $.ajax({
        type: "GET",
        data: {"Lat": latlng.getLat(), "Lng": latlng.getLng()},
        url: "/hospitaApi",
        dataType: "json",
        success: function (data) {
            mapApi(data);
        },
        error: function (error) {
            console.log(error);
        }
    })
});

function save() {
    var location_name = document.getElementById('name').innerText;
    var location = document.getElementById('location').innerText;
    var phoneNum = document.getElementById('phoneNum').innerText;
    var coordinate = document.getElementById('coordinate').innerText;

    $.ajax({
        type: "POST",
        data: {"location_name": location_name, "location": location, "phoneNum": phoneNum, "coordinate": coordinate},
        url: "/myLocation",
        success: function (list) {
            if (list[1] == 0) {
                alert("등록완료")
                scrollList.push(location_name);
                var cnt = parseInt(list[0])
                var data = {
                    location_name: location_name,
                    location: location,
                    phoneNum: phoneNum,
                    coordinate: coordinate,
                    num: cnt
                }
                html(data);
            } else if (list[1] == 1) {
                alert("이미 등록된 장소입니다.");
            }

        },
        error: function (error) {
            console.log(error);
        }
    })


}


//search 함수
$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/myLocation",
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                html(data[i]);
                scrollList.push(data[i].location_name);

            }
        },
        error: function (error) {
            console.log(error);
        }
    })
})


function delete_col(cnt) {
    var right_location_name = document.getElementById("a_" + cnt).innerText;
    var div_num = document.getElementById("div_" + cnt);
    $.ajax({
        type: "DELETE",
        data: {"location_name": right_location_name},
        url: "/myLocation",
        success: function (data) {
            if (data == "ok") {
                alert("삭제완료");
                div_num.style.display = "none";

            } else {
                alert("오류발생");
            }
        },
        error: function (error) {
            console.log(error);
        }
    })

}
var scrollchk = true;
var page = 1;
var mutex = false;
var totalList;

$("#table").scroll(function () {
    mutex = false;
    let $window = $(this);
    let scrollTop = $window.scrollTop();
    let windowHeight = $window.height();
    let documentHeight = $(document).height(); //전체 높이

    if (scrollchk) {
        if (scrollTop + windowHeight + 170 > documentHeight && !mutex) {
            mutex = true;
            onScroll("Data");
        }
    }
});
function cancel() {
    var nameDiv = document.getElementById('name');
    var locationDiv = document.getElementById('location');
    var phoneNumDiv = document.getElementById('phoneNum');
    var coordinateDiv = document.getElementById('coordinate');
    var saveBtn = document.getElementById("saveBtn");
    var canBtn = document.getElementById("canBtn");
    nameDiv.style.display = "none";
    locationDiv.style.display = "none";
    phoneNumDiv.style.display = "none";
    coordinateDiv.style.display = "none";
    saveBtn.style.display = "none";
    canBtn.style.display = "none";

}
function onScroll() {
    $.ajax({
        type: "GET",
        url: "/scroll",
        traditional: true,
        data: {"scrollList": scrollList},
        success: function (data) {
            const set = new Set(scrollList);
            scrollList = [...set];
            for (let i = 0; i < data.length; i++) {
                console.log("화면에 띄울거에요 ==" + data[i].location_name);
                scrollList.push(data[i].location_name);
            }

            for (let i = 0; i < data.length; i++) {
                if (data[i].location_name == "null") {
                    continue;
                }
                html(data[i]);

            }
        },
        error: function (error) {
            console.log(error);
        }
    })

}

function html(data) {
    // console.log(data);
    var html = "<div id=div_" + data.num + ">"
    html += "<div id=" + "a_" + data.num + ">" + data.location_name + "</div>"
    html += "<a>" + data.phoneNum + "</a>"
    html += "<input type='button' id='btn' onclick='delete_col(" + data.num + ")' value='삭제'/>"
    html += "</div>"
    $("#table").append(html);
}

function mapApi(data) {

    Object.keys(data).forEach(function (k) {
        for (var i = 0; i < k.length; i++) {
            // 마커를 생성하고 지도에 표시합니다


            var iwContent = '<div style="padding:5px;">' + data[k].yadmNm + '</div>',
                iwPosition = new kakao.maps.LatLng(data[k].YPos, data[k].XPos);
            marker = addMarker(iwPosition);

            var infowindow = new kakao.maps.InfoWindow({
                position: iwPosition,
                content: iwContent
            });
            infowindow.open(map, marker);

            kakao.maps.event.addListener(marker, 'click', function () {
                // 마커 위에 인포윈도우를 표시합니다
                var nameDiv = document.getElementById('name');
                var locationDiv = document.getElementById('location');
                var phoneNumDiv = document.getElementById('phoneNum');
                var coordinateDiv = document.getElementById('coordinate');
                var saveBtn = document.getElementById("saveBtn");
                var canBtn = document.getElementById("canBtn");
                nameDiv.innerHTML = data[k].yadmNm;
                locationDiv.innerHTML = data[k].addr;
                phoneNumDiv.innerHTML = data[k].telno;
                coordinateDiv.innerHTML = "(" + data[k].XPos + "/" + data[k].YPos + ")";
                saveBtn.style.display = "inline-block";
                canBtn.style.display = "inline-block";
                nameDiv.style.display = "block";
                locationDiv.style.display = "block";
            });
        }// end for

    })

}

function addMarker(position) {
    var markers = [];
    marker = new kakao.maps.Marker({
        position: position // 마커의 위치
    });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}
