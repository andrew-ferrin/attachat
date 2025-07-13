const setSystemPromptFromButton = button => {
    const content = button.getAttribute('data-content');
    document.getElementById('systemPrompt').value = content;
};

// naive conversion
const markdownToHtml = md => md
    .replace(/^# (.*$)/gim, '<h1>$1</h1>')
    .replace(/^## (.*$)/gim, '<h2>$1</h2>')
    .replace(/^### (.*$)/gim, '<h3>$1</h3>')
    .replace(/\*\*(.*)\*\*/gim, '<b>$1</b>')
    .replace(/\*(.*)\*/gim, '<i>$1</i>')
    .replace(/\n$/gim, '<br />');

const submitChat = () => {
    const systemPrompt = document.getElementById('systemPrompt').value;
    const prompt = document.getElementById('prompt').value;

    const waitingResponse = 'Waiting for response...';
    document.getElementById('responseContent').textContent = waitingResponse;
    document.getElementById('functionCalls').innerHTML = `<li>${waitingResponse}</li>`;

    fetch('/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            systemPrompt: systemPrompt,
            prompt: prompt
        })
    })
        .then(response => response.json())
        .then(({ content, functionCalls }) => {
            document.getElementById('responseContent').innerHTML = content ? markdownToHtml(content) : 'No content returned';
            const functionCallsList = document.getElementById('functionCalls');
            functionCallsList.innerHTML = '';
            if (functionCalls && functionCalls.length > 0) {
                functionCalls.forEach(({ name, arguments }) => {
                    const li = document.createElement('li');

                    li.textContent = `Function: ${name}:`;

                    const argList = document.createElement('ul');

                    Object.entries(JSON.parse(arguments)).forEach(([key, value]) => {
                        const argItem = document.createElement('li');
                        argItem.innerHTML = `<code>${key}</code>: <b>${value}</b>`;
                        argList.appendChild(argItem);
                    });

                    li.appendChild(argList);
                    functionCallsList.appendChild(li);
                });
            } else {
                functionCallsList.innerHTML = '<li>No tool calls made</li>';
            }
        })
        .catch(error => console.error('Error:', error));
};
