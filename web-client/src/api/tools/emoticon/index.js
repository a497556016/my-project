import BaseService from "../../base";

class EmoticonService extends BaseService{
    selectCatalogPage(params){
        return this.get('emoticonCatalog/selectPage', params);
    }

    selectImgPage(params) {
        return this.get('emoticonImg/selectPage', params);
    }
}

export default new EmoticonService('/emoticon-server/')