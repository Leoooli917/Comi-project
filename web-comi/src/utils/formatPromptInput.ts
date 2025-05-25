export const formatPromptInput = (text: string) => text.split(/[,;.，、。；]/).flatMap((s) => {
  const r = s.trim();
  return r ? [r] : [];
});
