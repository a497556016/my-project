<template>
    <div>
        <m-table v-model="assignedPermissions" :columns="columns" :page="false" >
            <a-switch slot="assign" slot-scope="{record}" v-model="record.assigned"></a-switch>
        </m-table>
    </div>
</template>

<script>
    import MTable from "../../../components/table/Table";
    import {mapState} from 'vuex'
    export default {
        name: "AssignPermission",
        components: {MTable},
        data(){
            return {
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '编码', dataIndex: 'code'},
                    {title: '操作', slot: 'assign'}
                ],
            }
        },
        computed: {
            ...mapState({
                assignedPermissions: state => state.role.assignedPermissions
            })
        }
    }
</script>

<style scoped>

</style>