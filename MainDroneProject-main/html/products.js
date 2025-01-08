// JavaScript code
// Initialize the Amazon Cognito credentials provider
AWS.config.region = "us-east-2";
AWS.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: "us-east-2:500331eb-f6d5-4a03-87ae-15dcf36f049c",
});

// Create S3 service object
var s3 = new AWS.S3();

// Parameters for listObjectsV2 call
var params = {
    Bucket: "drone-med",
};

// Retrieve the objects in the bucket
s3.listObjectsV2(params, function (err, data) {
    if (err) {
        console.log("Error listing objects: " + err);
    } else {
        // Show content and hide loading animation
        document.querySelector(".loading-screen").style.display = "none";
        document.getElementById("objectContainer").style.display = "flex";

        // Iterate through the objects and display them
        data.Contents.forEach(function (object) {
            var objectUrl =
                "https://" + params.Bucket + ".s3.amazonaws.com/" + object.Key;
            var imageElement = document.createElement("img");
            imageElement.src = objectUrl;
            imageElement.alt = object.Key;

            // Create a button for each image
            var button = document.createElement("button");
            button.className = "btn add-to-cart";
            button.setAttribute("data-product-id", object.Key); // Assuming the object key can serve as the product ID
            button.textContent = "Add to Cart";

            var link = document.createElement("a");
            link.href = "checkout.html"; // Replace with your add to cart endpoint
            link.appendChild(button);

            // Create a div to hold the image, price, and button
            var container = document.createElement("div");
            container.className = "objectContainerItem"; // Add a class to style the container
            container.appendChild(imageElement);

            // Retrieve the tags associated with the object
            var getObjectTagParams = {
                Bucket: params.Bucket,
                Key: object.Key,
            };

            s3.getObjectTagging(getObjectTagParams, function (err, tagData) {
                if (err) {
                    console.log("Error getting object tags: " + err);
                } else {
                    // Iterate through the tags to find the price tag
                    var priceValue = null;
                    tagData.TagSet.forEach(function (tag) {
                        if (tag.Key === "price") {
                            priceValue = tag.Value;
                        }
                    });

                    // Create a paragraph element to display the price
                    var priceElement = document.createElement("p");
                    priceElement.textContent = "price " + priceValue;
                    priceElement.style.fontFamily = "Abril Fatface";

                    document.body.appendChild(priceElement);

                    // Append the price element and button to the container
                    container.appendChild(priceElement);
                    container.appendChild(link);
                }
            });

            // Append the container to the main container
            document.getElementById("objectContainer").appendChild(container);
        });
    }
});
