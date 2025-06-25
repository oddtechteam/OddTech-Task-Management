import React, { useEffect, useState } from "react";

const ChatBot = () => {

  return (
    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
      intent="WELCOME"
      chat-title="ChatBot01"
      agent-id="43a58ae9-7364-47e2-8262-3a1eb686f293"
      language-code="en"
    ></df-messenger>
  );
};

export default ChatBot;
