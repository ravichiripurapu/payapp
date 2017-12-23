/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompensationTypeDetailComponent } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type-detail.component';
import { CompensationTypeService } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.service';
import { CompensationType } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.model';

describe('Component Tests', () => {

    describe('CompensationType Management Detail Component', () => {
        let comp: CompensationTypeDetailComponent;
        let fixture: ComponentFixture<CompensationTypeDetailComponent>;
        let service: CompensationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompensationTypeDetailComponent],
                providers: [
                    CompensationTypeService
                ]
            })
            .overrideTemplate(CompensationTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompensationTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompensationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompensationType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.compensationType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
