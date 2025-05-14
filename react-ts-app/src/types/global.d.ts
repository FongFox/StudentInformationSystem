export { };

declare global {
    interface IBackendRes<T> {
        status: number | string;
        message: string;
        error?: string | string[];
        data?: T;
    }

    interface IModelPaginate<T> {
        meta: {
            current: number;
            pageSize: number;
            pages: number;
            total: number;
        },
        results: T[]
    }

    interface ILogin {
        accessToken: string;
        studentProfileResponse: { id: number, code: number, fullName: string, username: string }
    }

}
