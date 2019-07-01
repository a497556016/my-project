import Mock from 'mockjs'

const Random = Mock.Random;

const getTopImages = function (options) {
    return {
        code: 1,
        data: [
            {id: 1, path: Random.dataImage('300x200', '首页展示')},
            {id: 2, path: Random.dataImage('300x200', '首页展示')},
            {id: 3, path: Random.dataImage('300x200', '首页展示')}
        ]
    }
}

export default {
    getTopImages
}