const copyToClipboard = () => {
    const jsonText = document.getElementById("jsonOutput")?.textContent;
    if (jsonText) {
        navigator.clipboard.writeText(jsonText).then(() => {
            alert("JSON copied to clipboard!");
        }).catch(error => {
            console.error("Error copying text to clipboard: ", error);
        });
    } else {
        console.error("Element with id 'jsonOutput' not found.");
    }
};