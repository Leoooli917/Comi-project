export const download = (url: string, filename?: string) => {
    fetch(url)
      .then(response => response.blob())
      .then(blob => {
        const objUrl = window.URL.createObjectURL(new Blob([blob]));
        const link = document.createElement('a');
        link.href = objUrl;
        link.setAttribute('download', filename || 'image.png');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      });
  };