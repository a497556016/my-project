export const ErrorResult = (msg) => {
    return Promise.resolve({
        code: 0,
        msg
    })
}