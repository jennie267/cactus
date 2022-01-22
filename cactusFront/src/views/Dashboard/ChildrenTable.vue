<template>
    <div class="card shadow" style="width:100%;">
        <div class="card-header box3Header">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-badge todaySchHeaderStr"></h1>  자녀목록</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%; height:100%;"
                        :columns="childHeader"
                        :rows="childData"
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
                childHeader: [
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
                childData: [],
            }
        },
        methods: {
            onRowClick(select) {
                this.$emit('selectUser', select.row)
            },
        },
        mounted() {
            this.$http.get(`/user/children/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                .then(res => {
                    this.childData = res.data.users;
                    this.childData.forEach(user => user.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                        '<img alt="Image placeholder" src="'+user.profileUrl+'" style="width:90%;"/>\n' +
                        '</a>');
                });
        }
    }
</script>

<style scoped>

</style>
