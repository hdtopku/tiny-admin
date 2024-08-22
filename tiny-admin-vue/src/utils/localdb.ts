import localforage from "localforage";


const chatHistoryDb =
    localforage.createInstance({
        name: 'chat',
        storeName: 'chatHistory',
        version: 1.0,
        description: 'localforage chat store',
        driver: [localforage.INDEXEDDB, localforage.WEBSQL, localforage.LOCALSTORAGE],
        size: 4980736,
    })
export {
    chatHistoryDb
}
