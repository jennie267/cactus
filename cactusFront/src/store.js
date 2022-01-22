import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export const store = new Vuex.Store({
    mutations: {
        setUser: function (state, payload) {state.user = payload},
        setInsertedPeriodId: function (state, payload) {state.insertedPeriodId = payload},
        setInsertedSchdId: function (state, payload) {state.insertedSchdId = payload},
        setUpdatedPeriodId: function (state, payload) {state.updatedPeriodId = payload},
        setUpdatedSchdId: function (state, payload) {state.updatedSchdId = payload},
        setDeletedPeriodId: function (state, payload) {state.deletedPeriodId = payload},
        setDeletedSchdId: function (state, payload) {state.deletedSchdId = payload},
        setDeletedChildId: function (state, payload) {state.deletedChildId = payload},
        setDeletedParentId: function (state, payload) {state.deletedParentId = payload},
    },
    getters: {
        getUser: function (state) {return state.user},
        getInsertedPeriodId: function (state) {return state.insertedPeriodId},
        getInsertedSchdId: function (state) {return state.insertedSchdId},
        getUpdatedPeriodId: function (state) {return state.updatedPeriodId},
        getUpdatedSchdId: function (state) {return state.updatedSchdId},
        getDeletedPeriodId: function (state) {return state.deletedPeriodId},
        getDeletedSchdId: function (state) {return state.deletedSchdId},
        getDeletedChildId: function (state) {return state.deletedChildId},
        getDeletedParentId: function (state) {return state.deletedParentId},
    },
    state: {
        user: {},
        parents: [],
        gateway: 'http://zuul.paas-ta.org/',
        insertedPeriodId: 0,
        insertedSchdId: 0,
        updatedPeriodId: 0,
        updatedSchdId: 0,
        deletedPeriodId: 0,
        deletedSchdId: 0,
        deletedChildId: 0,
        deletedParentId: 0,
    },
});