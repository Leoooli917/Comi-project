
interface StorageItem<T> {
    key?:string;
    value:T;
}
class StorageUtil {
    static setItem<T>(item:StorageItem<T>):void {
        localStorage.setItem(item.key,JSON.stringify(item.value));
    }

    static getItem<T>(key:string):T | null {
        const value = localStorage.getItem(key);
        return value ? JSON.parse(value): null;
    }

    static removeItem(key:string):void {
        localStorage.removeItem(key);
    }

    static clear():void {
        localStorage.clear();
    }
}

export default StorageUtil