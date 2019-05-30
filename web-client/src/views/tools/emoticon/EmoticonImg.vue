<template>
    <div>
        <curd-page :search-items="searchItems" :columns="columns" :load-fun="loadTableData">
            <template slot="src" slot-scope="{text, record, index}">
                <a target="_blank" :href="text">{{text}}</a>
            </template>
            <img slot="img" slot-scope="{text, record, index}" referrerpolicy="no-referrer" style="width: 100px;height: 100px;" @click="showImgModal(record)" :src="getImgSrc(record.path)">
        </curd-page>

        <a-modal title="查看图片" v-model="imgVisible">
            <img :src="imgSrc" style="width: 100px;height: 100px;">
        </a-modal>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import emoticonService from '../../../api/tools/emoticon';
    export default {
        name: "EmoticonImg",
        components: {CurdPage},
        data(){
            return {
                searchItems: [
                    {component: 'a-input', field: "like@catalog.catalog", placeholder: '类别'},
                    {component: 'a-input', field: 'like@title', placeholder: '图片标题'}
                ],
                columns: [
                    {title: '标题', dataIndex: 'title'},
                    {title: '类别', dataIndex: 'catalog.catalog'},
                    {title: '图片', dataIndex: 'img', slot: 'img'},
                    {title: '原始路径', dataIndex: 'src', slot: 'src', width: 100},
                ],
                imgVisible: false,
                imgSrc: ''
            }
        },
        methods: {
            loadTableData(params){
                return emoticonService.selectImgPage(params);
            },
            showImgModal(img){
                console.log(this)
                this.imgSrc = this.getImgSrc(img.path);
                this.imgVisible = true;
            },
            getImgSrc(path){
                return this.$baseURL + '/emoticon-server/emoticonImg/download?path=' + encodeURI(path);
            }
        }
    }
</script>

<style scoped>

</style>