/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { MaritalStatusDetailComponent } from '../../../../../../main/webapp/app/entities/marital-status/marital-status-detail.component';
import { MaritalStatusService } from '../../../../../../main/webapp/app/entities/marital-status/marital-status.service';
import { MaritalStatus } from '../../../../../../main/webapp/app/entities/marital-status/marital-status.model';

describe('Component Tests', () => {

    describe('MaritalStatus Management Detail Component', () => {
        let comp: MaritalStatusDetailComponent;
        let fixture: ComponentFixture<MaritalStatusDetailComponent>;
        let service: MaritalStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [MaritalStatusDetailComponent],
                providers: [
                    MaritalStatusService
                ]
            })
            .overrideTemplate(MaritalStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaritalStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaritalStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MaritalStatus(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.maritalStatus).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
