import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Gender } from './gender.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GenderService {

    private resourceUrl = SERVER_API_URL + 'api/genders';

    constructor(private http: Http) { }

    create(gender: Gender): Observable<Gender> {
        const copy = this.convert(gender);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(gender: Gender): Observable<Gender> {
        const copy = this.convert(gender);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Gender> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Gender.
     */
    private convertItemFromServer(json: any): Gender {
        const entity: Gender = Object.assign(new Gender(), json);
        return entity;
    }

    /**
     * Convert a Gender to a JSON which can be sent to the server.
     */
    private convert(gender: Gender): Gender {
        const copy: Gender = Object.assign({}, gender);
        return copy;
    }
}