import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CompensationType } from './compensation-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompensationTypeService {

    private resourceUrl = SERVER_API_URL + 'api/compensation-types';

    constructor(private http: Http) { }

    create(compensationType: CompensationType): Observable<CompensationType> {
        const copy = this.convert(compensationType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(compensationType: CompensationType): Observable<CompensationType> {
        const copy = this.convert(compensationType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompensationType> {
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
     * Convert a returned JSON object to CompensationType.
     */
    private convertItemFromServer(json: any): CompensationType {
        const entity: CompensationType = Object.assign(new CompensationType(), json);
        return entity;
    }

    /**
     * Convert a CompensationType to a JSON which can be sent to the server.
     */
    private convert(compensationType: CompensationType): CompensationType {
        const copy: CompensationType = Object.assign({}, compensationType);
        return copy;
    }
}
