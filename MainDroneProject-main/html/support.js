function openCoursesPage() {
    window.location.href = "courses.html";
}

document.querySelectorAll('input[name="year"]').forEach((radio) => {
    radio.addEventListener("change", (event) => {
        const fieldSection = document.getElementById("field-section");
        fieldSection.style.display = "block";
        fieldSection.style.opacity = 0;
        setTimeout(() => {
            fieldSection.style.opacity = 1;
        }, 10);
    });
});
function displayGreeting() {
    var greetingElement = document.querySelector(".incoming p");
    var today = new Date();
    var hourNow = today.getHours();
    var greeting;
    switch (true) {
        case hourNow > 18:
            greeting = "Good evening!";
            break;
        case hourNow > 12:
            greeting = "Good afternoon!";
            break;
        case hourNow > 0:
            greeting = "Good Morning";
            break;
        default:
            greeting = "Welcome!";
    }
    greetingElement.textContent = greeting + " How can I assist you today?";
}
window.onload = displayGreeting();

const mainChat = document.querySelector(".chatbox");
const chatInput = document.querySelector(".chat_footer textarea");
const sendChatBtn = document.querySelector(".sendBtn");
const chatbox = document.querySelector(".chatbox");

let userMsg;
const API_KEY = "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";
const inputHeight = chatInput.scrollHeight;

const createChatLi = (message, className) => {
    console.log("createChatLi called with message:", message);
    const chatLi = document.createElement("li");
    chatLi.classList.add("chat", className);
    let chatContent =
        className === "outgoing_chat"
            ? `<p>${message}</p>`
            : `<p>${message}</p>`;
    chatLi.innerHTML = chatContent;
    chatLi.querySelector("p").textContent = message;
    return chatLi;
};

const generateResponse = (incomingChatLi, dotInterval) => {
    const API_URL = "https://api.openai.com/v1/chat/completions";
    const messageElement = incomingChatLi.querySelector("p");

    const requestOptions = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${API_KEY}`,
        },
        body: JSON.stringify({
            model: "gpt-3.5-turbo",
            messages: [{ role: "user", content: userMsg }],
        }),
    };
    console.log("fetch start");
    fetch(API_URL, requestOptions)
        .then((res) => res.json())
        .then((data) => {
            clearInterval(dotInterval);
            messageElement.textContent = data.choices[0].message.content;
            console.log("Fetch end");
        })
        .catch((error) => {
            console.error("Error:", error);
            messageElement.textContent =
                "Oops! Something went wrong. Please try again.";
        })
        .finally(() => chatbox.scrollTo(0, chatbox.scrollHeight));
};

const handleChat = () => {
    console.log("handleChat called");
    userMsg = chatInput.value.trim();
    console.log("User message:", userMsg);
    if (!userMsg) return;
    chatInput.value = "";
    chatInput.style.height = `${inputHeight}px`;

    chatbox.appendChild(createChatLi(userMsg, "outgoing_chat"));
    chatbox.scrollTo(0, chatbox.scrollHeight);

    setTimeout(() => {
        const incomingChatLi = createChatLi(".", "incoming");
        chatbox.appendChild(incomingChatLi);
        chatbox.scrollTo(0, chatbox.scrollHeight);

        let dotCount = 1;
        const dotInterval = setInterval(() => {
            dotCount = (dotCount % 3) + 1;
            incomingChatLi.querySelector("p").textContent = ".".repeat(
                dotCount
            );
        }, 600);

        generateResponse(incomingChatLi, dotInterval);
    }, 600);
};

chatInput.addEventListener("input", () => {
    chatInput.style.height = `${inputHeight}px`;
    chatInput.style.height = `${chatInput.scrollHeight}px`;
});

chatInput.addEventListener("keydown", (e) => {
    if (e.key === "Enter" && !e.shiftKey && window.innerWidth > 800) {
        e.preventDefault();
        handleChat();
    }
});

sendChatBtn.addEventListener("keydown", (e) => {
    if (e.key === "Enter" && document.activeElement === sendChatBtn) {
        e.preventDefault();
        handleChat();
    }
});
sendChatBtn.addEventListener("click", handleChat);
//Code inspired by codingnepal

function changeText(element) {
    element.textContent = "Coming Soon!";
    element.style.color = "rgb(4, 255, 4)";
}
function originalText(element) {
    element.textContent = "Community";
    element.style.color = "white";
}
function changeButton(element) {
    element.textContent = "Coming Soon!";
    element.style.color = "rgb(4, 255, 4)";
}
function originalButton(element) {
    element.textContent = "Sign Up";
    element.style.color = "white";
}
