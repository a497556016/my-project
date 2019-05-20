<template>
    <div>
        <m-table v-model="roleList" :columns="columns" :page="false" >
            <a-switch slot="assign" slot-scope="{record}" v-model="record.assigned"></a-switch>
        </m-table>
    </div>
</template>

<script>
    import MTable from "../../../components/table/Table";

    import {mapState} from 'vuex'
    export default {
        name: "AssignRole",
        components: {MTable},
        data(){
            return {
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '操作', slot: 'assign'}
                ],
            }
        },
        computed: {
            ...mapState({
                roleList: state => state.user.roleList
            })
        },
        mounted(){

        },
        methods: {

        }
    }
</script>

<style scoped>

</style>