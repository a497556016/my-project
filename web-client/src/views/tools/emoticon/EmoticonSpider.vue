<template>
    <div>
        <m-table :columns="columns" :data="data" :page="false">
            <a slot="website" slot-scope="{text}" :href="text" target="_blank">{{text}}</a>
            <template slot="handler" slot-scope="{text, record, index}">
                <a-button-group>
                    <a-button>执行</a-button>
                    <a-button type="primary">编辑</a-button>
                </a-button-group>
            </template>
        </m-table>
    </div>
</template>

<script>
    import CurdPage from "../../../components/page/CurdPage";
    import MTable from "../../../components/table/Table";
    export default {
        name: "EmoticonSpider",
        components: {MTable, CurdPage},
        data(){
            return {
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '来源网站', dataIndex: 'website', slot: 'website'},
                    {title: 'cron规则', dataIndex: 'cron'},
                    {title: '操作', align: 'center', slot:'handler'}
                ],
                data: [
                    {name: '斗图网表情包', website: 'https://doutula.com', cron: '0 0 2 * * *'}
                ]
            }
        }
    }
</script>

<style scoped>

</style>