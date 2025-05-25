export const getParamsFromUrl = (url: string) => {
    const urlSplitArr = url.split('?')
    if(urlSplitArr.length < 2){
        return {}
    }

    const paramsArr = urlSplitArr[1].split('&')
    const paramsMap: { [key: string]: string } = {}
    for (var item of paramsArr) {
        const arr = item.split('=')
        paramsMap[arr[0]] = arr[1]
    }
    return paramsMap
}

export const getCurrentUrlParamValue = (key: string) => {
    const params = getParamsFromUrl(window.location.href)
    return params[key]
}