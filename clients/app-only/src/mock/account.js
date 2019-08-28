const Mock = require('mockjs');
const Random = Mock.Random;

export default {
    getLineUserInfo(){
        const data = {
            id: 6,
            nickname: '小敏',
            username: 'heshaowei',
            email: 'hehe@sina.com',
            qq: '123456789',
            phone: '13589564521'
        }
        return {
            code: 1,
            data,
            msg: 'success'
        }
    },
    selectUserList(){
        console.log(Mock)
        const size = Random.integer(0, 10);
        const datas = [];
        for (let i = 0; i < size; i++) {
            const data = {
                id: 6,
                nickname: Random.cname(),
                username: Random.name(),
                email: Random.email(),
                qq: '123456789',
                phone: '13589564521'
            }
            datas.push(data);
        }

        return {
            code: 1,
            data: datas,
            msg: 'success'
        }
    }
}