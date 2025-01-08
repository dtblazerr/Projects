var origin = { lat: 33.49618536306862, lng: -86.87284264531401 };
var map;
var droneMarker;
var routeCoordinates;
var stepIndex = 0;
var returnTrip = false;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: origin,
    });

    var urlParams = new URLSearchParams(window.location.search);
    var address = urlParams.get("address");
    var city = urlParams.get("city");
    var state = urlParams.get("state");
    var zip = urlParams.get("zip");
    var destinationAddress = `${address}, ${city}, ${state} ${zip};`;

    var geocoder = new google.maps.Geocoder();
    geocoder.geocode(
        { address: destinationAddress },
        function (results, status) {
            if (status === "OK") {
                var destination = results[0].geometry.location;
                var directionsService = new google.maps.DirectionsService();
                directionsService.route(
                    {
                        origin: origin,
                        destination: destination,
                        travelMode: "DRIVING",
                    },
                    function (response, status) {
                        if (status === "OK") {
                            var route = response.routes[0];
                            routeCoordinates = route.overview_path;
                            drawRoute(routeCoordinates);
                            animateDrone();
                        } else {
                            console.error(
                                "Directions request failed due to " + status
                            );
                        }
                    }
                );
            } else {
                console.error(
                    "Geocode was not successful for the following reason: " +
                        status
                );
            }
        }
    );
}

function drawRoute(coordinates) {
    var routePath = new google.maps.Polyline({
        path: coordinates,
        geodesic: true,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 2,
    });
    routePath.setMap(map);
}

function animateDrone() {
    droneMarker = new google.maps.Marker({
        position: routeCoordinates[0],
        map: map,
        icon: {
            url: "https://cdn-icons-png.flaticon.com/256/3273/3273701.png",
            scaledSize: new google.maps.Size(40, 40),
        },
        optimized: false,
    });

    moveDrone();
}

function moveDrone() {
    var timer = setInterval(function () {
        stepIndex++;
        if (stepIndex >= routeCoordinates.length) {
            if (!returnTrip) {
                displayMessage("Delivery Complete!");
                routeCoordinates.reverse();
                stepIndex = 0;
                returnTrip = true;
            } else {
                clearInterval(timer);
                displayMessage("Drone Returned");
                returnTrip = false; // Reset for potential future use
                stepIndex = 0; // Reset step index as well
                return;
            }
        }
        droneMarker.setPosition(routeCoordinates[stepIndex]);
    }, 130);
}

function displayMessage(message) {
    var messageDiv = document.getElementById("messageDiv");
    if (!messageDiv) {
        messageDiv = document.createElement("div");
        messageDiv.id = "messageDiv";
        messageDiv.style.position = "fixed";
        messageDiv.style.top = "10px";
        messageDiv.style.left = "50%";
        messageDiv.style.transform = "translateX(-50%)";
        messageDiv.style.padding = "10px 20px";
        messageDiv.style.backgroundColor = "#4CAF50";
        messageDiv.style.color = "white";
        messageDiv.style.fontSize = "20px";
        messageDiv.style.borderRadius = "5px";
        messageDiv.style.boxShadow = "0 2px 6px rgba(0,0,0,0.2)";
        document.body.appendChild(messageDiv);
    }
    messageDiv.innerHTML = message;
}
