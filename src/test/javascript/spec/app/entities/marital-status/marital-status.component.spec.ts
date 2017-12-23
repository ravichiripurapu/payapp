/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { MaritalStatusComponent } from '../../../../../../main/webapp/app/entities/marital-status/marital-status.component';
import { MaritalStatusService } from '../../../../../../main/webapp/app/entities/marital-status/marital-status.service';
import { MaritalStatus } from '../../../../../../main/webapp/app/entities/marital-status/marital-status.model';

describe('Component Tests', () => {

    describe('MaritalStatus Management Component', () => {
        let comp: MaritalStatusComponent;
        let fixture: ComponentFixture<MaritalStatusComponent>;
        let service: MaritalStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [MaritalStatusComponent],
                providers: [
                    MaritalStatusService
                ]
            })
            .overrideTemplate(MaritalStatusComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaritalStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaritalStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MaritalStatus(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.maritalStatuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
