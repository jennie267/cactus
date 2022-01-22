<template>
    <!-- Modals -->
    <div class="row">
        <base-button class="btn-primary" @click="showModal()">등록</base-button>
        <div class="col-md-4">
            <modal :show.sync="modals.modal1">
                <h2 slot="header" class="modal-title" id="modal-title-default">일정 등록</h2>
                <form>
                    <div class="form-group row" v-if="user.type==='CHILD'">
                        <label for="periodType" class="col-sm-3 col-form-label">부모님</label>
                        <div class="col-sm-6">
                            <multiselect v-model="selectedParent" track-by="name" label="name" placeholder="선택" :options="this.$store.state.parents" :searchable="false" :allow-empty="true">
                                <template slot="singleLabel" slot-scope="{ option }"><strong>{{ option.name }}</strong></template>
                            </multiselect>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodName" class="col-sm-2 col-form-label">일정명</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodName" v-model="period.name">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodRemark" class="col-sm-2 col-form-label">내용</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodRemark" v-model="period.remark">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">알람여부</label>
                        <div class="col-sm-9">
                            <div class="custom-control custom-checkbox mb-3">
                                <input class="custom-control-input" id="isAlarm" type="checkbox" v-model="isAlarm">
                                <label class="custom-control-label" for="isAlarm">
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">반복일정</label>
                        <div class="col-sm-9" valign="middle">
                            <div class="custom-control custom-checkbox mb-3">
                                <input class="custom-control-input" id="repSchedule1" type="checkbox" v-model="isCycle">
                                <label class="custom-control-label" for="repSchedule1">
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">날짜</label>
                        <base-input class="col-sm-5" addon-left-icon="ni ni-calendar-grid-58">
                            <flat-pickr slot-scope="{focus, blur}"
                                        @on-open="focus"
                                        @on-close="blur"
                                        :config="{allowInput: true}"
                                        class="form-control datepicker"
                                        v-model="startDate">
                            </flat-pickr>
                        </base-input>
                        <base-input class="col-sm-5">
                            <flat-pickr slot-scope="{focus, blur}"
                                        @on-open="focus"
                                        @on-close="blur"
                                        :config=timeConfig
                                        class="form-control datepicker"
                                        v-model="startTime">
                            </flat-pickr>
                        </base-input>
                    </div>
                    <div class="form-group" v-if="isCycle">
                        <div class="form-group row">
                            <div class="col-sm-2"><h1>~</h1></div>
                            <base-input class="col-sm-5" addon-left-icon="ni ni-calendar-grid-58">
                                <flat-pickr slot-scope="{focus, blur}"
                                            @on-open="focus"
                                            @on-close="blur"
                                            locale="ko"
                                            :config="{allowInput: true}"
                                            class="form-control datepicker"
                                            v-model="endDate">
                                </flat-pickr>
                            </base-input>
                            <base-input class="col-sm-5">
                                <flat-pickr slot-scope="{focus, blur}"
                                            @on-open="focus"
                                            @on-close="blur"
                                            :config=timeConfig
                                            class="form-control datepicker"
                                            v-model="endTime">
                                </flat-pickr>
                            </base-input>
                        </div>
                        <div class="form-group row">
                            <label for="periodFreq" class="col-sm-2 col-form-label">주기</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="periodFreq" v-model="period.freq">
                            </div>
                            <div class="col-sm-6">
                                <multiselect v-model="cycle" track-by="name" label="name" placeholder="선택" :options="options" :searchable="false" :allow-empty="true">
                                    <template slot="singleLabel" slot-scope="{ option }"><strong>{{ option.name }}</strong></template>
                                </multiselect>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodPlace" class="col-sm-2 col-form-label">장소</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodPlace" v-model="period.place">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodManager" class="col-sm-2 col-form-label">담당자</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodManager" v-model="period.manager">
                        </div>
                    </div>
                </form>
                <template slot="footer">
                    <base-button type="primary" class="active btn-primary" @click="confirm">등록</base-button>
                    <base-button type="secondary" class="active ml-3" @click="modals.modal1 = false">취소
                    </base-button>
                </template>
            </modal>
        </div>
    </div>
</template>
<script>
    import Modal from "@/components/Modal.vue";
    import flatPickr from 'vue-flatpickr-component';
    import Multiselect from 'vue-multiselect'
    import 'flatpickr/dist/flatpickr.css';
    import 'vue-multiselect/dist/vue-multiselect.min.css'

    export default {
        components: {
            Modal
            , flatPickr
            , Multiselect
        },
        props: {
            date: String,
            selectedParent: Object,
        },
        updated() {
            this.startDate = this.date;
        },
        watch: {
            startDate: function() {
                this.period.startTime = this.startDate===""?"":this.startDate+'T'+this.startTime;
            },
            startTime: function() {
                this.period.startTime = this.startDate===""?"":this.startDate+'T'+this.startTime;
            },
            endDate: function() {
                this.period.endTime = this.endDate===""?"":this.endDate+'T'+this.endTime;
            },
            endTime: function() {
                this.period.endTime = this.endDate===""?"":this.endDate+'T'+this.endTime;
            },
            isAlarm: function() {
                this.period.isAlarm = this.isAlarm===true?"Y":"N";
            },
            isCycle: function() {
                if(this.isCycle===false) {
                    this.endDate = "";
                    this.endTime = "";
                    this.cycle = null;
                    this.period.freq = "";
                }
            },
            cycle: function() {
                this.period.cycle = this.cycle===null?"":this.cycle.value;
            },
            selectedParent: function() {
                this.period.userId = this.selectedParent===null?"":this.selectedParent.userId;
            }
        },
        data() {
            return {
                modals: {
                    modal1: false,
                },
                user: this.$store.state.user,
                isAlarm: false,
                isCycle: false,
                timeConfig:{
                    allowInput: true,
                    enableTime: true,
                    noCalendar: true,
                    dateFormat: "H:i",
                    defaultDate: "00:00"
                },
                config: {
                    wrap: true,
                    altFormat: 'M j, Y',
                    altInput: true,
                    dateFormat: 'Y-m-d',
                },
                value: null,
                options: [
                    { name: '년', value:'Y'},
                    { name: '월', value:'M'},
                    { name: '주', value:'W'},
                    { name: '일', value:'D'},
                    { name: '시간', value:'H'},
                    { name: '분', value:'MM'},
                ],
                startDate: "",
                startTime: "00:00",
                endDate: "",
                endTime: "00:00",
                cycle: {},
                period: {
                    name:"",
                    remark: "",
                    periodType: "",
                    isAlarm: "N",
                    startTime: "",
                    endTime: "",
                    freq: "",
                    cycle: "",
                    place: "",
                    manager: "",
                    userId: this.$store.state.user.type==='PARENT'?this.$store.state.user.userId:"",
                    regUserId: this.$store.state.user.userId,
                    isUse: "Y",
                }
            };
        },
        methods:{
            showModal(){
                this.period =                {
                    name:"",
                        remark: "",
                        periodType: "",
                        isAlarm: "N",
                        startTime: "",
                        endTime: "",
                        freq: "",
                        cycle: "",
                        place: "",
                        manager: "",
                        userId: this.$store.state.user.type==='PARENT'?this.$store.state.user.userId:"",
                        regUserId: this.$store.state.user.userId,
                        isUse: "Y",
                };
                this.modals.modal1=true;
            },
            confirm() {
                this.$http.post(`/period/`, this.period,
                    {
                        headers: {
                            Authorization: `Bearer ${this.user.token}`,
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                    )
                    .then(res => {
                        if(res.status===200) {
                            this.$swal({
                                type: 'success',
                                title: '등록되었습니다.'
                            });
                            this.modals.modal1 = false;

                            let period = res.data;
                            this.$http.post(`/period/schedule/period/${period.periodId}`, period,
                                {
                                    headers: {
                                        Authorization: `Bearer ${this.user.token}`,
                                        'Content-Type': 'application/json;charset=UTF-8'
                                    }
                                }
                            ).then(res => {
                                if(res.status===200) {
                                    this.$store.commit('setInsertedPeriodId', period.periodId);
                                }
                            });
                        }
                    });
            }
        }
    };

</script>
<style>
</style>
