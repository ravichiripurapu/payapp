import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyLocation } from './company-location.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyLocationService {

    private resourceUrl = SERVER_API_URL + 'api/company-locations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyLocation: CompanyLocation): Observable<CompanyLocation> {
        const copy = this.convert(companyLocation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyLocation: CompanyLocation): Observable<CompanyLocation> {
        const copy = this.convert(companyLocation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyLocation> {
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
     * Convert a returned JSON object to CompanyLocation.
     */
    private convertItemFromServer(json: any): CompanyLocation {
        const entity: CompanyLocation = Object.assign(new CompanyLocation(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyLocation to a JSON which can be sent to the server.
     */
    private convert(companyLocation: CompanyLocation): CompanyLocation {
        const copy: CompanyLocation = Object.assign({}, companyLocation);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyLocation.createdDate);
        return copy;
    }
}
