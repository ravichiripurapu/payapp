import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyEarning } from './company-earning.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyEarningService {

    private resourceUrl = SERVER_API_URL + 'api/company-earnings';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyEarning: CompanyEarning): Observable<CompanyEarning> {
        const copy = this.convert(companyEarning);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyEarning: CompanyEarning): Observable<CompanyEarning> {
        const copy = this.convert(companyEarning);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyEarning> {
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
     * Convert a returned JSON object to CompanyEarning.
     */
    private convertItemFromServer(json: any): CompanyEarning {
        const entity: CompanyEarning = Object.assign(new CompanyEarning(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyEarning to a JSON which can be sent to the server.
     */
    private convert(companyEarning: CompanyEarning): CompanyEarning {
        const copy: CompanyEarning = Object.assign({}, companyEarning);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyEarning.createdDate);
        return copy;
    }
}
