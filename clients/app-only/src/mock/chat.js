const Mock = require('mockjs');
const Random = Mock.Random;

export default {
    queryHisRecordList(){
        const list = [];
        const size = Random.int(0, 10);
        const users = ['heshaowei', 'moshan'];
        for (let i = 0; i < size; i++) {
            const fromIndex = Random.int(0, 1);
            list.push({
                id: Random.int(),
                avatar: Random.image(),
                from: users[fromIndex],
                to: users[1-fromIndex],
                msg: Random.cparagraph(1, 2),
                time: Random.date()
            })
        }
        return {
            code: 1,
            data: list
        }
    }
}