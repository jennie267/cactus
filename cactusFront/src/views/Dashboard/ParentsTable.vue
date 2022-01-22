<template>
    <div class="card shadow" style="width:100%;">
        <div class="card-header box3Header">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-badge todaySchHeaderStr"></h1>  부모님목록</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%; height:100%;"
                        :columns="parentHeader"
                        :rows="parentData"
                        @on-row-click="onRowClick"/>
            </div>
        </div>
    </div>
</template>

<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table/src'

    export default {
        components: {
            VueGoodTable,
        },
        data() {
            return {
                user: this.$store.state.user,
                parentHeader: [
                    {
                        field: 'userId',
                        hidden: true,
                    },
                    {
                        label: '사진',
                        field: 'profile',
                        width: '8%',
                        tdClass: 'text-center',
                        html: true,
                    },
                    {
                        label: '이름',
                        field: 'name',
                        width: '25%',
                    },
                ],
                parentData: [],
            }
        },
        methods: {
            onRowClick(select) {
                this.$emit('selectUser', select.row)
            },getParents(){
                this.$http.get(`/user/parents/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                    .then(res => {
                        this.parentData = res.data.users;
                        this.parentData.forEach(user => user.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                            '<img alt="" src="'+user.profileUrl+'" style="width:90%;"/>\n' +
                            '</a>');
                    });
            }
        },
        mounted() {
            this.getParents();
        }
    }
</script>

<style scoped>

</style>
